/*
  Shield_Wrapper.cpp - Library for wrapping Ethernet.
  Created by Kiucheol Shin(kiucheol.shin@kt.com), November 20, 2015.
  Released into the public domain.
*/

#include <WiFi.h>
#include <SPI.h>
#include "Shield_Wrapper.h"

WiFiClient __client;

Shield_Wrapper::Shield_Wrapper(){
	_status = WL_IDLE_STATUS;
}

void Shield_Wrapper::begin(const char* ssid, const char* pass)
{
	Serial.println(F("begin WiFi!\n")); 
	if (WiFi.status() == WL_NO_SHIELD) {
		Serial.println("WiFi shield not present"); 
		while(true);
	} 
	Serial.print(F("firmware:")); Serial.println(WiFi.firmwareVersion()); 


	while ( _status != WL_CONNECTED) {
		Serial.print(F("connect to "));Serial.println("Router");
		// WPA/WAP2 네트워크에 연결
		_status = WiFi.begin((char*)ssid, pass);
		delay(1000);
	}
}

void Shield_Wrapper::disconnect(){
	WiFi.disconnect();
}

void Shield_Wrapper::print(){
	Serial.print(F("IP: "));Serial.println(WiFi.localIP());
	Serial.print(F("GW: "));Serial.println(WiFi.gatewayIP());
}

Client* Shield_Wrapper::getClient(){
	return (Client*)&__client;
}
