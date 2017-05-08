package ws

import (
	"bytes"
	"errors"
	"fmt"
	"net/cpollet/itinerants/cli/net"
)

func NewEventResource(server net.RemoteServer, authToken *AuthToken) EventResource {
	return eventResource{RemoteServer: server, AuthToken: authToken}
}

type EventResource interface {
	Future() (string, error)
}

type eventResource struct {
	RemoteServer net.RemoteServer
	AuthToken    *AuthToken
}

func (resource eventResource) Future() (string, error) {
	result, err := resource.RemoteServer.Get("/events/future", &net.Token{Token: resource.AuthToken.Token})
	if err != nil {
		return "", err
	}

	if result.HttpCode() != 200 {
		return "", errors.New(fmt.Sprintf("Unable to fetch: %d", result.HttpCode()))
	}

	buf := new(bytes.Buffer)
	buf.ReadFrom(result.Content())

	return string(buf.String()), nil
}
