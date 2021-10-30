#!/usr/bin/python3

import os
import requests

JB_URL = 'https://codetest.jurosbaixos.com.br/v1/fizzbuzz/'

def get_fb_number(headers):
    return requests.get(JB_URL, headers=headers).json()

def get_api_key():
    api_key = os.getenv('JB_API_KEY')
    if not api_key:
        api_key = input('Please give API key:')
    return api_key

def get_jb_headers(api_key):
    return {'X-API-KEY': api_key}

if __name__ == '__main__':
    jb_headers = get_jb_headers(get_api_key())
    fb_number = get_fb_number(jb_headers)    
    print (fb_number)
