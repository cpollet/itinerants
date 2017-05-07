package ws

import (
	"bytes"
	"encoding/json"
	"errors"
	"fmt"
	"net/cpollet/itinerants/cli/net"
)

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
type AuthToken struct {
	Token string
}

type SessionResource struct {
	RemoteServer *net.RemoteServer
}

func (resource *SessionResource) Authenticate(username string, password string) (*AuthToken, error) {
	payload, err := json.Marshal(sessionRequest{
		Username: username,
		Password: password,
	})
	if err != nil {
		return nil, err
	}

	result, err, _ := resource.RemoteServer.Put("sessions", bytes.NewReader(payload), nil)

	if err != nil {
		return nil, err
	}

	var response sessionResponse
	if err := json.NewDecoder(bytes.NewReader(result)).Decode(&response); err != nil {
		return nil, errors.New(fmt.Sprintf("Unable to parse JSON: %s", err))
	}

	if response.Result != "SUCCESS" {
		return nil, errors.New("Invalid username or password")
	}

	return &AuthToken{response.Token}, nil
}
