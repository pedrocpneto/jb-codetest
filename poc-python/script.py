#!/usr/bin/python3


import os
import requests
import hashlib
import json
import sys

from requests.models import HTTPError
from retry import retry

JB_URL = 'https://codetest.jurosbaixos.com.br/v1/fizzbuzz/'

def raise_not_ok(response):
    obj = response.json()
    if response.status_code != 200:
        # it seems 'No puzzle' is a valid response even in post and delete requests
        if obj['errors'][0]['message'] != 'No puzzle':
            raise HTTPError(f'{response.status_code} Error: {response.reason}', response=response)
    elif ('errors' in obj):
        raise HTTPError('200 response with errors')


def reset(headers):
    response = requests.get(f'{JB_URL}reset', headers=headers)
    print(f'reset: {response.status_code}', response.text)

@retry((HTTPError, ValueError))
def get_numbers(headers):
    response = requests.get(JB_URL, headers=headers)
    print(f'get_numbers: {response.status_code}', response.text)
    raise_not_ok(response)
    obj = response.json()
    return obj

@retry((HTTPError, ValueError))
def post_hash(headers, resolved_puzzle):
    hash = json_sha_256(resolved_puzzle)
    response = requests.post(f'{JB_URL}{hash}', json=resolved_puzzle, headers=headers)
    print(f'post_hash: {response.status_code}', response.text)
    raise_not_ok(response)
    return hash

@retry((HTTPError, ValueError))
def delete_range(headers, hash):
    response = requests.delete(f'{JB_URL}{hash}', headers=headers)
    print(f'delete_range: {response.status_code}', response.text)
    raise_not_ok(response)

@retry((HTTPError, ValueError))
def get_can_i_has_treasure(headers, hash):
    response = requests.get(f'{JB_URL}{hash}/canihastreasure', headers=headers)
    print(f'get_can_i_has_treasure: {response.status_code}', response.text)
    raise_not_ok(response)
    obj = response.json()
    return response.status_code == 200 and 'treasure' in obj, obj

def fizz_buzz(number):
    result = ''
    if number % 3 == 0:
        result += 'fizz'
    if number % 5 == 0:
        result += 'buzz'
    if not result:
        result = str(number)
    return result

def get_api_key():
    api_key = os.getenv('JB_API_KEY')
    if not api_key:
        api_key = input('Please give API key:')
    return api_key

def json_sha_256(obj):
    return hashlib.sha256(json.dumps(obj, separators=[',',':']).encode('utf-8')).hexdigest()

if __name__ == '__main__':
    jb_headers = {'X-API-KEY': get_api_key()}
    
    if len(sys.argv) > 1 and sys.argv[1] == 'reset':
        reset(jb_headers)
        exit(0)

    iteration = 1
    while True:
        print (f'iteration {iteration}')
        iteration+=1
        
        numbers = get_numbers(jb_headers)   
        
        fb_numbers = list(map(fizz_buzz, numbers))
        print(f'fb_numbers: {fb_numbers[:10]} ...')
        
        # print(f'SHA256: {json_sha_256(list(map(fizz_buzz, [3,5,15])))}') # c66a63862cf416c2acfe81ae697c066cff80b430af31fc9cae70957f355ded7d
        print(f'SHA256: {json_sha_256(fb_numbers)}')
        hash = post_hash(jb_headers, fb_numbers)
        
        delete_range(jb_headers, hash)
        
        has_treasure, value = get_can_i_has_treasure(jb_headers, hash)
        if has_treasure:
            print(f'TREASURE!: {value}')
            break
        else:
            print('No treasure yet :/')
