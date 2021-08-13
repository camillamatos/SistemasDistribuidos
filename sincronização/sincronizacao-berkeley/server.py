import numpy as np
from socket import socket, AF_INET, SOCK_STREAM
from utils import get_current_time, get_time_in_seconds, seconds_to_time_string
from time import sleep
from data import ADDR

class Server:
  def __init__(self, num_clients):
    self.num_clients = num_clients
    self.clients = []
    self.server = None
    self.sync_time()

  def sync_time(self):
    self.time = get_current_time()

  def start(self):
    self.server = socket(AF_INET, SOCK_STREAM)
    self.server.bind(ADDR)
    self.server.listen()

    for i in range(self.num_clients):
      print(f'Aguardando {i + 1}º cliente...')
      conn, addr = self.server.accept()
      print(f'Cliente {addr} conectou.')
      self.clients.append((conn, addr))
    self.listen()

  def close(self):
    for client in self.clients:
      conn, addr = client
      conn.close()

    self.server.close()

  def get_times(self):
    request = 'get_time'
    times = []

    for client in self.clients:
      conn, addr = client
      conn.send(request.encode())
      response = conn.recv(1024).decode()
      times.append(response)

    return times

  def set_times(self, time):
    request = 'set_time'

    for client in self.clients:
      conn, addr = client
      conn.send(request.encode())
      conn.recv(1024)
      conn.send(time.encode())

  def calc_time(self, times):
    hour, minute, second = self.time
    server_time = (int(hour) * 3600) + (int(minute) * 60) + int(second)
    times_in_seconds = []
    used_times = []

    for time in times:
      seconds = get_time_in_seconds(time)
      times_in_seconds.append(seconds)

    for time in times_in_seconds:
      if(time <= server_time + 600 and time >= server_time - 600):
        used_times.append(time)

    new_time = (np.sum(used_times) + server_time) / (len(times) + 1)

    return seconds_to_time_string(new_time)

  def listen(self):
    while(True):
      print(f'server: {self.time}')
      times = self.get_times()
      print(f'clients: {times}')
      new_time = self.calc_time(times)
      self.set_times(new_time)
      sleep(10)
      self.sync_time()

def main():
  server = Server(num_clients=4)

  try:
    server.start()
  except KeyboardInterrupt:
    server.close()
  finally:
    server.close()

  server.close()

if __name__ == '__main__':
  main()
