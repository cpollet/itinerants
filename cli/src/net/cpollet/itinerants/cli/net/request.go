package net

import (
	"bytes"
	"errors"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
)

type Token struct {
	Token string
}

func NewRemoteServer(baseUrl string) RemoteServer {
	return remoteServer{baseUrl: baseUrl}
}

type RemoteServer interface {
	Put(string, io.Reader, *Token) (Response, error)
	Get(string, *Token) (Response, error)
}

type remoteServer struct {
	baseUrl string
}

func (remote remoteServer) Put(path string, payload io.Reader, token *Token) (Response, error) {
	request, err := build("PUT", remote.baseUrl+path, payload, token)
	if err != nil {
		return nil, err
	}

	return send(request)
}

func (remote remoteServer) Get(path string, token *Token) (Response, error) {
	request, err := build("GET", remote.baseUrl+path, nil, token)
	if err != nil {
		return nil, err
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

func send(request *http.Request) (Response, error) {
	client := new(http.Client)
	r, err := client.Do(request)
	if err != nil {
		return nil, errors.New(fmt.Sprintf("Unable to execute request: %s", err))
	}

	defer r.Body.Close()

	body, err := ioutil.ReadAll(r.Body)

	return NewResponse(r.StatusCode, bytes.NewReader(body)), nil
}
