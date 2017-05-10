package net

import (
	"bytes"
	"io"
	"io/ioutil"
	"net/http"
)

func NewRemoteServer(baseUrl string) RemoteServer {
	return remoteServer{baseUrl: baseUrl}
}

type RemoteServer interface {
	Get(path string, token string) (Response, error)
	Put(path string, payload io.Reader, token string) (Response, error)
	Post(path string, payload io.Reader, token string) (Response, error)
}

type remoteServer struct {
	baseUrl string
}

func (remote remoteServer) Get(path string, token string) (Response, error) {
	request, err := build("GET", remote.baseUrl+path, nil, token)
	if err != nil {
		return nil, err
	}

	return send(request)
}

func (remote remoteServer) Put(path string, payload io.Reader, token string) (Response, error) {
	request, err := build("PUT", remote.baseUrl+path, payload, token)
	if err != nil {
		return nil, err
	}

	return send(request)
}

func (remote remoteServer) Post(path string, payload io.Reader, token string) (Response, error) {
	request, err := build("POST", remote.baseUrl+path, payload, token)
	if err != nil {
		return nil, err
	}

	return send(request)
}

func build(method string, path string, payload io.Reader, token string) (*http.Request, error) {
	request, err := http.NewRequest(method, path, payload)
	if err != nil {
		return nil, err
	}
	request.Header.Add("Content-Type", "application/json")

	if token != "" {
		request.Header.Add("X-Auth-Token", token)
	}

	return request, nil
}

func send(request *http.Request) (Response, error) {
	client := new(http.Client)
	r, err := client.Do(request)
	if err != nil {
		return nil, err
	}

	defer r.Body.Close()

	body, err := ioutil.ReadAll(r.Body)

	return NewResponse(r.StatusCode, bytes.NewReader(body)), nil
}
