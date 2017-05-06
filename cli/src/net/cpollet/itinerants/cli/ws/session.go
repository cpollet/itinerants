package ws

import (
	"bytes"
	"encoding/json"
	"errors"
	"fmt"
	"net/cpollet/itinerants/cli/net"
)

type SessionRequest struct {
	Username string `json:"username"`
	Password string `json:"password"`
}
type SessionResponse struct {
	Token    string   `json:"token"`
	Roles    []string `json:"roles"`
	PersonID string   `json:"personId"`
	Result   string   `json:"result"`
}
type AuthToken struct {
	Token string
}

func Token(username string, password string) (*AuthToken, error) {
	payload, err := json.Marshal(SessionRequest{
		Username: username,
		Password: password,
	})
	if err != nil {
		return nil, err
	}

	result, err, _ := net.Request("PUT", "sessions", payload)
	if err != nil {
		return nil, err
	}

	var sessionResponse SessionResponse
	if err := json.NewDecoder(bytes.NewReader(result)).Decode(&sessionResponse); err != nil {
		return nil, errors.New(fmt.Sprintf("Unable to parse JSON: %s", err))
	}

	if sessionResponse.Result != "SUCCESS" {
		return nil, errors.New("Invalid username or password")
	}

	return &AuthToken{sessionResponse.Token}, nil
}
