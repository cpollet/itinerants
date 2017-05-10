package ws

import (
	"bytes"
	"encoding/json"
	"errors"
	"fmt"
	"net/cpollet/itinerants/cli/net"
)

func NewSessionResource(server net.RemoteServer) SessionResource {
	return sessionResource{RemoteServer: server}
}

type SessionResource interface {
	Authenticate(string, string) (Token, error)
}

type sessionResource struct {
	RemoteServer net.RemoteServer
}

func (resource sessionResource) Authenticate(username string, password string) (Token, error) {
	payload, err := json.Marshal(sessionRequest{
		Username: username,
		Password: password,
	})
	if err != nil {
		return nil, err
	}

	result, err := resource.RemoteServer.Put("sessions", bytes.NewReader(payload), "")

	if err != nil {
		return nil, err
	}

	var response sessionResponse
	if err := json.NewDecoder(result.Content()).Decode(&response); err != nil {
		return nil, err
	}

	if result.HttpCode() == 401 || response.Result == "INVALID_CREDENTIALS" {
		return nil, errors.New("Invalid username or password")
	}

	if result.HttpCode() != 200 {
		return nil, errors.New(fmt.Sprintf("Server answered with %d", result.HttpCode()))
	}

	if response.Result != "SUCCESS" {
		return nil, errors.New(fmt.Sprintf("Server answered %s", response.Result))
	}

	return NewToken(response.Token), nil
}

type sessionRequest struct {
	Username string `json:"username"`
	Password string `json:"password"`
}
type sessionResponse struct {
	Token    string   `json:"token"`
	Roles    []string `json:"roles"`
	PersonID string   `json:"personId"`
	Result   string   `json:"result"`
}
