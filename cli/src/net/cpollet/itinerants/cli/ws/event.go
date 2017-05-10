package ws

import (
	"bytes"
	"errors"
	"fmt"
	"net/cpollet/itinerants/cli/net"
	"time"
	"encoding/json"
)

const dateTimeFormat = "2006-01-02T15:04:00"

func NewEventResource(server net.RemoteServer, authToken Token) EventResource {
	return eventResource{
		RemoteServer: server,
		AuthToken:    authToken,
	}
}

type EventResource interface {
	Future() (string, error)
	Create(name string, dateTime time.Time) (error)
}

type eventResource struct {
	RemoteServer net.RemoteServer
	AuthToken    Token
}

func (resource eventResource) Future() (string, error) {
	result, err := resource.RemoteServer.Get("/events/future", resource.AuthToken.Value())
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

func (resource eventResource) Create(name string, dateTime time.Time) (error) {
	fmt.Printf("Creating %s, on %s\n", name, dateTime.Format(dateTimeFormat))

	payload, err := json.Marshal(createRequest{
		Name:     name,
		DateTime: dateTime.Format(dateTimeFormat),
	})
	if err != nil {
		return err
	}

	response, err := resource.RemoteServer.Post("events", bytes.NewReader(payload), resource.AuthToken.Value())

	if err != nil {
		return err
	}

	if response.HttpCode() != 200 {
		return errors.New(fmt.Sprintf("Server answered with %d", response.HttpCode()))
	}

	return nil
}

type createRequest struct {
	Name     string `json:"name"`
	DateTime string `json:"dateTime"`
}
