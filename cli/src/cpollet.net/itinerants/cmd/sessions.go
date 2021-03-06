package cmd

import (
	"cpollet.net/itinerants/helpers"
	"cpollet.net/itinerants/net"
	"cpollet.net/itinerants/prefs"
	"cpollet.net/itinerants/ws"
	"fmt"
	"golang.org/x/crypto/ssh/terminal"
	"strings"
	"syscall"
)

func Login(program string, args []string) {
	if len(args) != 2 {
		helpers.Dief("Usage: %s login <username> <server>", program)
	}

	username, args := helpers.Pop(args)
	baseUrl, args := helpers.Pop(args)

	if !strings.HasSuffix(baseUrl, "/") {
		baseUrl = baseUrl + "/"
	}

	fmt.Printf("Logging in on %s as %s ...\n", baseUrl, username)

	fmt.Print("Password: ")
	bytePassword, err := terminal.ReadPassword(int(syscall.Stdin))
	if err != nil {
		helpers.Die("Unable to read password")
	}
	fmt.Println()
	password := string(bytePassword)

	sessionResource := ws.NewSessionResource(
		net.NewRemoteServer(baseUrl),
	)

	token, err := sessionResource.Authenticate(username, password)
	if err != nil {
		helpers.Dief("Unable to authenticate: %s", err)
	}

	err = prefs.Save(prefs.Preferences{
		ServerUrl: baseUrl,
		Token:     token.Value(),
	})
	if err != nil {
		helpers.Dief("Unable to save preferences: %s", err)
	}

	fmt.Println("Login successful")
}
