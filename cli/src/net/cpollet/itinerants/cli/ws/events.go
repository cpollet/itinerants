package ws

import (
	"errors"
	"fmt"
	"net/cpollet/itinerants/cli/net"
)

func FutureEvents(token *AuthToken) (string, error) {
	result, err, statusCode := net.AuthenticatedRequest("GET", "events/future", net.Token{Token: token.Token}, nil)
	if err != nil {
		return "", err
	}

	if statusCode != 200 {
		return "", errors.New(fmt.Sprintf("Unable to fetch: %d", statusCode))
	}

	return string(result), nil
}
