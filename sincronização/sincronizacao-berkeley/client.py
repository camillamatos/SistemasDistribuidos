import numpy as np
from socket import socket, AF_INET, SOCK_STREAM
from utils import format_time, get_current_time, get_time_in_seconds, seconds_to_time_string
from data import ADDR

class Client:
  def __init__(self):
    self.client = None
    h, m, s = get_current_time()
    self.set_time(h, m, s)

  def connect(self):
    self.client = socket(AF_INET, SOCK_STREAM)
    self.client.connect(ADDR)

  def disconnect(self):
    self.client.close()

  def get_time(self):
    hour, minute, second = self.time
    return f'{hour}:{minute}:{second}'

  def set_time(self, hour, minute, second):
    self.time = (hour, minute, second)

  def listen(self):
    while(True):
      response = self.client.recv(1024).decode()

      if(response == 'get_time'):
        self.client.send(self.get_time().encode())
      elif(response == 'set_time'):
        self.client.send('ready'.encode())
        new_time = self.client.recv(1024).decode()
        hour, minute, second = new_time.split(':')
        old_time = get_time_in_seconds(self.get_time())
        curr_time = get_time_in_seconds(new_time)
        self.set_time(hour, minute, second)
        diff = np.abs(curr_time - old_time)

        print(f'Hora atual: {format_time((hour, minute, second))}')

        if (curr_time > old_time):
          print(f'Seu relógio precisa ser adiantado em {seconds_to_time_string(diff)}')
        elif (curr_time < old_time):
          print(f'Seu relógio precisa ser atrasado em {seconds_to_time_string(diff)}')
        else:
          print(f'Seu relógio está sincronizado')

        self.client.send(''.encode())

def main():
  client = Client()

  try:
    client.connect()
    client.listen()
  finally:
    client.disconnect()

if __name__ == '__main__':
  main()
