#include <SoftwareSerial.h>

int TxPin = 2;
int RxPin = 3;


//lenPin101_1 means that room number: 101 & first Led
//Please change the pin number when you test it
int ledPin101_1 = 4;
int ledPin101_2 = 5;
int ledPin101_3 = 6;
int ledPin101_4 = 7;
int ledPin102_1 = 8;
int ledPin102_2 = 9;
int ledPin102_3 = 10;
int ledPin102_4 = 11;
SoftwareSerial BTSerial(TxPin, RxPin); 


void setup()  
{
  BTSerial.begin(9600);

  pinMode(ledPin101_1,OUTPUT);
  pinMode(ledPin101_2,OUTPUT); 
  pinMode(ledPin101_3,OUTPUT); 
  pinMode(ledPin101_4,OUTPUT); 
  pinMode(ledPin102_1,OUTPUT); 
  pinMode(ledPin102_2,OUTPUT); 
  pinMode(ledPin102_3,OUTPUT); 
  pinMode(ledPin102_4,OUTPUT);  


}

void loop()
{
  if (BTSerial.available()){
    char cmd = (char)BTSerial.read();
    if(cmd == '1') {
  
           digitalWrite(ledPin101_1,LOW);
          digitalWrite(ledPin101_2,LOW);
          digitalWrite(ledPin101_3,LOW);
          digitalWrite(ledPin101_4,LOW);
    } 
    else if(cmd == '2') {
    digitalWrite(ledPin101_1,HIGH);
          digitalWrite(ledPin101_2,HIGH);
          digitalWrite(ledPin101_3,HIGH);
          digitalWrite(ledPin101_4,HIGH);
    } 
    else if(cmd == '3') {
      digitalWrite(ledPin101_1,LOW);
    } 
    else if(cmd == '4') {
     digitalWrite(ledPin101_1,HIGH);
      }
    else if(cmd == '5') {
      digitalWrite(ledPin102_1, LOW);
      }
    else if(cmd == '6') {
      digitalWrite(ledPin101_2, LOW);
      }
    else if(cmd == '7') {
      digitalWrite(ledPin101_3, LOW);
      }
    else if(cmd == '8') {
      digitalWrite(ledPin101_4, LOW);
      }
    else if(cmd == '9') {
      digitalWrite(ledPin102_1, HIGH);
      }
    else if(cmd == '12') {
      digitalWrite(ledPin102_2, HIGH);
      }
    else if(cmd == '13') {
      digitalWrite(ledPin102_3, HIGH);
      }
    else if(cmd == '14') {
      digitalWrite(ledPin102_4, HIGH);
      }
    else if(cmd =='15') {
      digitalWrite(ledPin102_1, LOW);
      }
    else if(cmd == '16') {
      digitalWrite(ledPin102_2, LOW);
      }
    else if(cmd == '17') {
      digitalWrite(ledPin102_3, LOW);
      }
    else if(cmd == '18') {
      digitalWrite(ledPin102_4, LOW);
      }
      
      //if cmd '101`, it means that all LED in room #101 turn on
     else if(cmd == '101') {
          digitalWrite(ledPin101_1,HIGH);
          digitalWrite(ledPin101_2,HIGH);
          digitalWrite(ledPin101_3,HIGH);
          digitalWrite(ledPin101_4,HIGH);
      }
      //if cmd '102`, it means that all LED in room #102 turn on
     else if(cmd == '102') {
          digitalWrite(ledPin102_1,HIGH);
          digitalWrite(ledPin102_2,HIGH);
          digitalWrite(ledPin102_3,HIGH);
          digitalWrite(ledPin102_4,HIGH);
      }
      //if cmd '101`, it means that all LED in room #101 turn off
     else if(cmd == '-101') {
          digitalWrite(ledPin101_1,LOW);
          digitalWrite(ledPin101_2,LOW);
          digitalWrite(ledPin101_3,LOW);
          digitalWrite(ledPin101_4,LOW);
      }
      //if cmd '102`, it means that all LED in room #102 turn off
    else if(cmd == '-102') {
          digitalWrite(ledPin102_1,LOW);
          digitalWrite(ledPin102_2,LOW);
          digitalWrite(ledPin102_3,LOW);
          digitalWrite(ledPin102_4,LOW);
      }
         
    }
}
