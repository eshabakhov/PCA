import random
import requests
import time
from datetime import datetime


def generate_phone_number():
    first = str(random.randint(100, 999))
    second = str(random.randint(1, 888)).zfill(3)

    last = (str(random.randint(1, 9998)).zfill(4))
    while last in ['1111', '2222', '3333', '4444', '5555', '6666', '7777', '8888']:
        last = (str(random.randint(1, 9998)).zfill(4))

    return f'{first}-{second}-{last}'


def generate_inn():
    return ''.join(random.choice('123456789') for _ in range(12))


if __name__ == '__main__':
    s = requests.session()
    s.post('http://localhost:8080/api/login', data={'username': 'admin', 'password': 'admin'})

    cities = ['Пермь', 'Санкт-Петербург', 'Москва', 'Ижевск', 'Рыбинск', 'Калининград']
    cities_r = s.get('http://localhost:8080/api/cities/list').json()['list']
    for city in cities:
        for city_r in cities_r:
            if city == city_r['name']:
                break
        else:
            r = s.post('http://localhost:8080/api/cities', json={'nightRate': f'{random.randrange(5, 50)}',
                                                                 'dayRate': f'{random.randrange(5, 50)}',
                                                                 'name': city})
    cities_r = s.get('http://localhost:8080/api/cities/list').json()['list']

    for i in range(5):
        r = s.post('http://localhost:8080/api/abonents', json={'phoneNumber': generate_phone_number(),
                                                               'inn': generate_inn(),
                                                               'address': f'Адрес {random.randrange(0, 1500)}'})
    abonents_r = s.get('http://localhost:8080/api/abonents/list').json()['list']

    while True:
        r = s.post('http://localhost:8080/api/calls', json={'abonentId': random.choice(abonents_r)['id'],
                                                            'cityId': random.choice(cities_r)['id'],
                                                            'datetime': datetime.now().strftime(
                                                                "%Y-%m-%dT%H:%M:%S.%fZ"),
                                                            'minutes': f'{random.randrange(1, 90)}'})
        print(r.json())
        time.sleep(5)
