import sys, socket

HOST = ''
PORT = 8888

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# try:
s.bind((HOST,PORT))
# except socket.error as e:
    # print ("connection error")
    # sys.exit()

s.listen(10)

while True:
    conn, addr = s.accept()
    print('Connected with ' + addr[0] + ':' + str(addr[1]))
    msg = conn.recv(4096)
    print(msg.decode('utf-8'))
