package main

import (
	"fmt"
	"golang.org/x/crypto/ssh/terminal"
	"log"
	"net/cpollet/itinerants/cli/net"
	"net/cpollet/itinerants/cli/prefs"
	"net/cpollet/itinerants/cli/ws"
	"os"
	"strings"
	"syscall"
)

// Available commands are:
// - login <username> <server>
// - future
func main() {
	if len(os.Args) < 2 {
		fmt.Print("Usage: login|future\n")
		os.Exit(1)
	}

	command := os.Args[1]

	switch command {
	case "login":
		if len(os.Args) != 4 {
			fmt.Printf("Usage: %s %s <username> <server>\n", os.Args[0], os.Args[1])
			os.Exit(1)
		}
		login(os.Args[3], os.Args[2])
		break
	case "future":
		future()
		break
	default:
		fmt.Print("Usage: login|future\n")
		os.Exit(1)
	}
}

func login(baseUrl string, username string) {
	if !strings.HasSuffix(baseUrl, "/") {
		baseUrl = baseUrl + "/"
	}

	fmt.Printf("Logging in on %s as %s ...\n", baseUrl, username)

	fmt.Print("Password: ")
	bytePassword, err := terminal.ReadPassword(int(syscall.Stdin))
	if err != nil {
		fmt.Print("Unable to read password")
		os.Exit(1)
	}
	fmt.Println()
	password := string(bytePassword)

	sessionResource := ws.NewSessionResource(
		net.NewRemoteServer(baseUrl),
	)

	token, err := sessionResource.Authenticate(username, password)
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

	eventResource := ws.NewEventResource(
		net.NewRemoteServer(preferences.ServerUrl),
		&ws.AuthToken{Token: preferences.Token},
	)

	result, err := eventResource.Future()
	if err != nil {
		log.Fatal(err)
	}

	fmt.Printf("Result: %s\n", result)
}
