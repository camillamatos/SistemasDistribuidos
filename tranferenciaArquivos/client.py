import socket
import sys

IP = sys.argv[1]
PORT = int(sys.argv[2])
FILE = sys.argv[3]
ADDR = (IP, PORT)
FORMAT = 'utf-8'

def main ():
  client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  client.connect(ADDR)
  print('Conectado!')
  client.send(FILE.encode(FORMAT))
  print('Requisitando o arquivo', FILE, "ao servidor.")
  file = open(FILE, 'w')
  data = client.recv(1024).decode(FORMAT)

  file.write(data)
  print('Documento recebido!')
  print('Conexão encerrada.')
  file.close()
  client.close()

if __name__ == '__main__':
  main()

#EXECUÇÃO
#python3 server.py 127.0.0.1 1234 arquivos
#python3 client.py 127.0.0.1 1234 exemplo.txt