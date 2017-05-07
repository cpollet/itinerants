package main

import (
	"fmt"
	"log"
	"net/cpollet/itinerants/cli/net"
	"net/cpollet/itinerants/cli/prefs"
	"net/cpollet/itinerants/cli/ws"
	"os"
	"strings"
)

// Available commands are:
// - login <server>
// - future
func main() {
	if len(os.Args) < 2 {
		fmt.Print("Usage: login|future\n")
		os.Exit(1)
	}

	command := os.Args[1]

	switch command {
	case "login":
		if len(os.Args) != 3 {
			fmt.Printf("Usage: %s %s <server>\n", os.Args[0], os.Args[1])
			os.Exit(1)
		}
		login(os.Args[2])
		break
	case "future":
		future()
		break
	default:
		fmt.Print("Usage: login|future\n")
		os.Exit(1)
	}
}

func login(baseUrl string) {
	fmt.Printf("Logging in on %s ...\n", baseUrl)

	if !strings.HasSuffix(baseUrl, "/") {
		baseUrl = baseUrl + "/"
	}

	sessionResource := ws.SessionResource{
		RemoteServer: &net.RemoteServer{BaseUrl: baseUrl},
	}

	token, err := sessionResource.Authenticate("cpollet", "password")
	if err != nil {
		log.Fatal(err)
	}

	err = prefs.Save(prefs.Preferences{
		ServerUrl: baseUrl,
		Token:     token.Token,
	})
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println("Login successful")
}

func future() {
	preferences, err := prefs.Load()
	if err != nil {
		log.Fatal(err)
	}

	if preferences == nil {
		fmt.Printf("Please login before executing this command with: %s login <server>\n", os.Args[0])
		os.Exit(1)
	}

	eventResource := ws.EventResource{
		RemoteServer: &net.RemoteServer{BaseUrl: preferences.ServerUrl},
		AuthToken:    &ws.AuthToken{Token: preferences.Token},
	}

	result, err := eventResource.Future()
	if err != nil {
		log.Fatal(err)
	}

	fmt.Printf("Result: %s\n", result)
}
