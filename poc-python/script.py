#!/usr/bin/python3

import os
import requests

JB_URL = 'https://codetest.jurosbaixos.com.br/v1/fizzbuzz/'

def get_numbers(headers):
    return requests.get(JB_URL, headers=headers).json()

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

def get_jb_headers(api_key):
    return {'X-API-KEY': api_key}

if __name__ == '__main__':
    jb_headers = get_jb_headers(get_api_key())
    numbers = get_numbers(jb_headers)   
    print(f'numbers: {numbers}')
    fb_numbers = list(map(fizz_buzz, numbers))
    print(f'fb numbers: {fb_numbers}')
