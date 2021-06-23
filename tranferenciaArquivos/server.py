import socket
import sys

IP = sys.argv[1]
PORT = int(sys.argv[2])
DIR = sys.argv[3]
ADDR = (IP, PORT)
FORMAT = 'utf-8'

def main ():
  server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  server.bind(ADDR)
  server.listen()
  print('Aguardando conexões...')

  conn, addr = server.accept()
  print('Cliente conectado.')
  filename = conn.recv(1024).decode(FORMAT)
  file = open(f'{DIR}/{filename}', 'r')
  print('Cliente requisitou arquivo', filename, "do diretório", DIR)

  data = file.read()
  conn.send(data.encode(FORMAT))
  print('Documento sendo enviado... pronto!')

  print('Conexão encerrada.')
  file.close()
  conn.close()
  server.close()

if __name__ == '__main__':
  main()

#EXECUÇÃO
#python3 server.py 127.0.0.1 1234 arquivos
#python3 client.py 127.0.0.1 1234 exemplo.txt