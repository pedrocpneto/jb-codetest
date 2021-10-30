#!/usr/bin/python3

import os
import requests
import hashlib
import json

JB_URL = 'https://codetest.jurosbaixos.com.br/v1/fizzbuzz/'


def get_numbers(headers):
    return requests.get(JB_URL, headers=headers).json()

def post_hash(headers, resolved_puzzle):
    response = requests.post(f'{JB_URL}{json_sha_256(resolved_puzzle)}', json=resolved_puzzle, headers=headers)
    response.raise_for_status()

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
    numbers = get_numbers(jb_headers)   
    print(f'numbers: {numbers}')
    fb_numbers = list(map(fizz_buzz, numbers))
    print(f'fb numbers: {fb_numbers}')
    # print(f'SHA256: {json_sha_256(list(map(fizz_buzz, [3,5,15])))}') # c66a63862cf416c2acfe81ae697c066cff80b430af31fc9cae70957f355ded7d
    print(f'SHA256: {json_sha_256(fb_numbers)}')
    post_hash(jb_headers, fb_numbers)

