#include <WiFi.h>

IPAddress ip(192, 168, 43, 150);
IPAddress gateway(192, 168, 43, 1);
IPAddress subnet(255, 255, 255, 0);
IPAddress dns(192, 168, 43, 1);

#define potentio 34
#define led1 26
#define led2 27
#define led3 25


const char* ssid     = "OPPO F7 128GB";
const char* password = "12345678";

WiFiServer server(80);

void setup()
{
    Serial.begin(115200);
    pinMode(led1, OUTPUT);
    pinMode(led2, OUTPUT);
    pinMode(led3, OUTPUT);
    pinMode(potentio, INPUT);    

    delay(10);

    // We start by connecting to a WiFi network

    Serial.println();
    Serial.println();
    Serial.print("Connecting to ");
    Serial.println(ssid);

    WiFi.config(ip,dns,gateway,subnet);
    WiFi.begin(ssid, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println("");
    Serial.println("WiFi connected.");
    Serial.println("IP address: ");
    Serial.println(WiFi.localIP());
    
    server.begin();

}

int value = 0;

void loop(){
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  Serial.println("New Client."); 
  while(!client.available()) {
    delay(1);
  }

  String request = client.readStringUntil('\r');
  Serial.print(request);
  

  if(request.indexOf("led1") != -1 ) {
    digitalWrite(led1, !digitalRead(led1));
  }
  if(request.indexOf("led2") != -1 ) {
    digitalWrite(led2, !digitalRead(led2));
  }
  if(request.indexOf("led3") != -1 ) {
    digitalWrite(led3, !digitalRead(led3));
  }
  
  int sensorvalue = analogRead(potentio);
  Serial.print(sensorvalue);

  
   client.println("HTTP/1.1 200 OK");
   client.println("Content-type:text/html");
   client.println("");
   
   
    client.print(",");
    client.print(sensorvalue);
    delay(1);
  
  
}

 
  
            
