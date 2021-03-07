/*
  Shield_Wrapper.cpp - Library for wrapping Ethernet.
  Created by Kiucheol Shin(kiucheol.shin@kt.com), November 20, 2015.
  Released into the public domain.
*/

#ifndef SHIELD_WRAPPER_H
#define SHIELD_WRAPPER_H

#include <Arduino.h>
#include <Client.h>

class Shield_Wrapper
{
	public:
		Shield_Wrapper();
		void begin(const char* ssid, const char* pass);
		void begin(const byte* mac, const IPAddress ip);
		void disconnect();

		void print();
		Client* getClient();
	private:
		int _status;
};

#endif
