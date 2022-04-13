# 정보 보안 과제 

## RSA 암호화 메시지/파일 소켓 통신 서버 클라이언트

## 테스트 방법

Server와 Client를 따로 나누었습니다.

![image](https://user-images.githubusercontent.com/49195475/163255427-afb4a722-e106-4e6c-b0ee-d866225f45f1.png)
![image](https://user-images.githubusercontent.com/49195475/163255699-c0be8f79-4404-476a-b239-908a7f551752.png)


### 메시지 암호화 테스트 방법 

* MainServer.js를 먼저 실행하신 후에 MainClient.js를 실행해주세요
* ![image](https://user-images.githubusercontent.com/49195475/163256246-daf03f81-2f52-42c2-98fd-f6630d142905.png)
* 그림의 빨강색 동그라미에 위치해 있는 텍스트 입력칸에 암호화 할 텍스트를 입력해주세요.
* Encrypt message 버튼을 클릭해주세요
* Encrypt message 버튼을 클릭하면 암호화된 문장이 아래의 텍스트 창에 표시됩니다
* Send버튼을 누르면 Server GUI에 암호화된 메시지가 전송되었다는 문장을 확인할 수 있습니다.
* Decrypt버튼을 눌러 복호화 하여 평문을 확인할 수 있습니다.

### 파일 암호화 테스트 방법
* MainServer.js를 먼저 실행하신 후에 MainClient.js를 실행해주세요
* client/src/ 아래에 있는 file.txt에 내용을 확인합니다.
* ![image](https://user-images.githubusercontent.com/49195475/163256246-daf03f81-2f52-42c2-98fd-f6630d142905.png)
* 그림의 빨강색 동그라미에 위치해 있는 텍스트 입력칸에 file.txt를 입력해주세요.
* Encrypt File 버튼을 통해 암호화 한 후 Send버튼을 눌러 서버에 전송해주세요.
* 서버에서 암호화된 파일을 수신했다는 알림을 확인할 수 있고,
* Decrypt 버튼을 통하여 파일에 적혀있는 평문을 확인할 수 있습니다.
* 그리고 server/src/ 디렉토리 아래에 복호화된 텍스트가 들어가있는 file.txt가 생성됩니다.


