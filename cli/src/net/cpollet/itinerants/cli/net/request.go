package net

import (
	"errors"
	"fmt"
	"io/ioutil"
	"net/http"
	"io"
)

type Token struct {
	Token string
}

func Request(method string, path string, payload io.Reader) ([]byte, error, int) {
	request, err := build(method, path, payload)
	if err != nil {
		return nil, err, 0
	}

	return send(request)
}

func build(method string, path string, payload io.Reader) (*http.Request, error) {
	baseUrl := "http://localhost:8080/"
	request, err := http.NewRequest(method, baseUrl+path, payload)
	if err != nil {
		return nil, errors.New(fmt.Sprintf("Unable to create request: %s", err))
	}
	request.Header.Add("Content-Type", "application/json")

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

func AuthenticatedRequest(method string, path string, token Token, payload io.Reader) ([]byte, error, int) {
	request, err := build(method, path, payload)
	if err != nil {
		return nil, err, 0
	}

	request.Header.Add("X-Auth-Token", token.Token)

	return send(request)
}
