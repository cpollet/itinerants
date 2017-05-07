package net

import (
	"errors"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
)

type Token struct {
	Token string
}

type RemoteServer struct {
	BaseUrl string
}

func (remote *RemoteServer) Put(path string, payload io.Reader, token *Token) ([]byte, error, int) {
	request, err := build("PUT", remote.BaseUrl+path, payload, token)
	if err != nil {
		return nil, err, 0
	}

	return send(request)
}

func (remote *RemoteServer) Get(path string, token *Token) ([]byte, error, int) {
	request, err := build("GET", remote.BaseUrl+path, nil, token)
	if err != nil {
		return nil, err, 0
	}

	return send(request)
}

func build(method string, path string, payload io.Reader, token *Token) (*http.Request, error) {
	request, err := http.NewRequest(method, path, payload)
	if err != nil {
		return nil, errors.New(fmt.Sprintf("Unable to create request: %s", err))
	}
	request.Header.Add("Content-Type", "application/json")

	if token != nil {
		request.Header.Add("X-Auth-Token", token.Token)
	}

	return request, nil
}

func send(request *http.Request) ([]byte, error, int) {
	client := &http.Client{}
	response, err := client.Do(request)
	if err != nil {
		return nil, errors.New(fmt.Sprintf("Unable to execute request: %s", err)), 0
	}

	defer response.Body.Close()

	body, err := ioutil.ReadAll(response.Body)

	if response.StatusCode != 200 {
		return body, errors.New(fmt.Sprintf("Server answered %d", response.StatusCode)), response.StatusCode
	}

	return body, nil, 200
}
