package ws

import (
	"errors"
	"fmt"
	"net/cpollet/itinerants/cli/net"
)

type EventResource struct {
	RemoteServer *net.RemoteServer
	AuthToken    *AuthToken
}

func (resource *EventResource) Future() (string, error) {
	result, err, statusCode := resource.RemoteServer.Get("/events/future", &net.Token{Token: resource.AuthToken.Token})
	if err != nil {
		return "", err
	}

	if statusCode != 200 {
		return "", errors.New(fmt.Sprintf("Unable to fetch: %d", statusCode))
	}

	return string(result), nil
}
