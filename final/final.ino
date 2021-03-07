#include <Arduino.h>
#include <SPI.h>
#include <WiFi.h>

#include "IoTMakers.h" //Iotplatform에서 제공되는 헤더파일
#include "Shield_Wrapper.h" 
#include "DHT.h"

#define DHTPIN 3
#define DHTTYPE DHT22

#define LED 4
DHT dht(DHTPIN, DHTTYPE); // 온도측정 센서 사용을 위한 선언

int cnt = 0;
float cur_t = 0.0f;
float ex_t = 0.0f;
float dif_t = 0.0f;

/*
Arduino Shield
*/

Shield_Wrapper   g_shield; //와이파이 쉴드를 컨트롤 하기 위한 객체 선언

#define SDCARD_CS   4

void sdcard_deselect()
{
   pinMode(SDCARD_CS, OUTPUT);
   digitalWrite(SDCARD_CS, HIGH); //Deselect the SD card
}
void init_shield() // 와이파이 쉴드에 Wifi ID, passwd 등을 설정하는 부분
{
   sdcard_deselect();
   
#if 1  // Using WiFi
   const char* WIFI_SSID = "CSEE_LAB"; // 와이파이 쉴드에 인터넷 연결을 위한 설정부분
   const char* WIFI_PASS = "1234qwer"; // 와이파이 쉴드에 인터넷 연결을 위한 설정부분
   g_shield.begin(WIFI_SSID, WIFI_PASS); // 인터넷 연결 시작

#else  // 이더넷을 사용하는 경우
   const byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
   // Set the static IP address to use if the DHCP fails to assign
   const IPAddress ip(192, 168, 0, 177);
   g_shield.begin(mac, ip);
#endif
   
   g_shield.print();
}


/*
IoTMakers
*/
IoTMakers g_im;

const char deviceID[]   = "mjkim9D1470282055719"; // Iot platform을 통해 받아온 아두이노 장비의 아이디
const char authnRqtNo[] = "ff69tie74"; // 서버와 연동을 위한 인증번호
const char extrSysID[]  = "OPEN_TCP_001PTL001_1000002514"; // 서버와 연동을 위한 인증번호 

void init_iotmakers()
{
   Client* client = g_shield.getClient(); //입력했던 클라이언트 정보와 서버에 등록되어 있는 클라이언트 정보를 받아와서 비교
   if ( client == NULL )   {
      Serial.println(F("No client from shield."));
      while(1);
   }

   g_im.init(deviceID, authnRqtNo, extrSysID, *client); //서버로부터 받아온 각종 정보를 통해 서버와 통신연결 함수
   g_im.set_numdata_handler(mycb_numdata_handler); // 아두이노를 통해 수신한 데이터를 서버로 보내는 함수
   g_im.set_strdata_handler(mycb_strdata_handler); // 아두이노를 통해 수신한 데이터를 서버로 보내는 함수
   g_im.set_dataresp_handler(mycb_resp_handler); // 아두이노를 통해 수신한 데이터를 서버로 보내는 함수

   // IoTMakers 서버 연결
   Serial.println(F("connect()..."));
   while ( g_im.connect() < 0 ){
      Serial.println(F("retrying."));
      delay(3000);
   }

   // Auth

   Serial.println(F("auth."));
   while ( g_im.auth_device() < 0 ) { //연결이 되지 않았다면 연결 실패를 호출
      Serial.println(F("fail"));
      while(1);
   }

   g_im.getFreeRAM(); // 현재 아두이노상의 RAM 공간을 표시
}

#define PIN_LED      9

void setup() 
{
   Serial.begin(115200);
     while ( !Serial )  {
     ;
   }

   pinMode(PIN_LED, OUTPUT);
   pinMode(PUMP, OUTPUT);

   init_shield(); //연결 초기화
   
   init_iotmakers(); //iotmakers 함수 호출

   dht.begin(); // 온도센서 작동 시작

 

}

void loop()
{
   static unsigned long tick = millis(); // 반복문 실행시마다 시간 측정

   
   if ( ( millis() - tick) > 3000 )  // 3초마다 조건문을 실행하기 위한 조건
   {
      send_temperature(); // 현재 온도센서를 통해 측정된 데이터 전달
      tick = millis(); // 지속적으로 현재 시간을 체크
    }
  
   // IoTMakers 서버 수신처리 및 keepalive 송신
   g_im.loop(); 
}

 

long microsecondsToCentimeters(long microseconds)
{
// The speed of sound is 340 m/s or 29 microseconds per centimeter.
// The ping travels out and back, so to find the distance of the
// object we take half of the distance travelled.
return microseconds / 29 / 2;
}

 

/*int send_brightness()
{
  int CDS_value;
  if(digitalRead(CDS)==LOW)
  {
    
   digitalWrite(LED, LOW);
    Serial.println("dark!");
    
    
    
    CDS_value=analogRead(A0);
  Serial.print("CDS value: "); Serial.println(CDS_value);
  if(g_im.send_numdata("CDS", (double)CDS_value)<0){
    Serial.println(F("fail"));
    return -1;
  }
    
    
    
  }
  else 
  { 
    
   
   digitalWrite(LED, HIGH);
    Serial.println("Bright!"); 
    
    
    
    CDS_value=analogRead(A0);
  Serial.print("CDS value: "); Serial.println(CDS_value);
  if(g_im.send_numdata("CDS", (double)CDS_value)<0){
    Serial.println(F("fail"));
    return -1;
  }
    
    
    
  }

 
return 0;

  
}
*/

int send_temperature()
{

        if((cnt > 0)){ //과거 온도값과 현재 온도값의 차이를 비교하는 부분
          ex_t = cur_t;
          
          Serial.print(F("Before Temperature (c): ")); Serial.println(ex_t);
          
          if ( g_im.send_numdata("beforeTemper", (double)ex_t) < 0) {
                 Serial.println(F("fail"));  
      return -1;
          }
        }  
          
        //delay(2000);
       
        cur_t = dht.readTemperature(); //센서의 온도를 읽는 부분
        float cur_h = dht.readHumidity(); // 센서의 습도를 읽는 부분
            
      
      
   Serial.print(F("Temperature (c): ")); Serial.println(cur_t);    
   if ( g_im.send_numdata("temperature", (double)cur_t) < 0 ) {
        Serial.println(F("fail"));  //읽어들인 정보를 Iot platform으로 전달
      return -1;
   }
   Serial.print(F("Humidity (%): ")); Serial.println(cur_h);
   if(g_im.send_numdata("humidity", (double)cur_h)<0){ 
   //읽어들인 정보를 Iot platform으로 전달
    Serial.println(F("fail"));
    return -1;
   }

        if((cnt > 0)){
          dif_t = ex_t - cur_t;
          Serial.print(F("Temperature Difference (c): ")); Serial.println(dif_t);
          if ( g_im.send_numdata("diffTemper", (double)dif_t) < 0 ){
                 Serial.println(F("fail"));  
      return -1;
          }
        }
        
        cnt = cnt + 1;
   return 0;   
}

int send_light() // 조명센서의 값을 서버로 보내는 부분
{
   int tmpVal = analogRead(A1);
   int light = tmpVal/4;

   Serial.print(F("Light : ")); Serial.println(light);
   if ( g_im.send_numdata("light", (double)light) < 0 ){
        Serial.println(F("fail"));  
      return -1;
   }
   return 0;   
}

void mycb_numdata_handler(char *tagid, double numval)
{
  
}
void mycb_strdata_handler(char *tagid, char *strval)
{ //만약 서버를 통해 수신한 데이터의 tagid가 LED 이고 해당 메시지가 on 혹은 off라면 해당 전구를 켜거나 끄는 함수 
   if ( strcmp(tagid, "led")==0 && strcmp(strval, "on")==0 )     
      digitalWrite(PIN_LED, HIGH);
   else if ( strcmp(tagid, "led")==0 && strcmp(strval, "off")==0 )     
      digitalWrite(PIN_LED, LOW);
   
}
void mycb_resp_handler(long long trxid, char *respCode)
{
   if ( strcmp(respCode, "100")==0 )
      Serial.println("resp:OK");
   else
      Serial.println("resp:Not OK");
}
