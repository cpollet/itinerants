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

func pop(args []string) (string, []string) {
	if len(args) == 0 {
		return "", []string{}
	}

	return args[0], args[1:]
}

func main() {
	program, args := pop(os.Args)
	command, args := pop(args)

	switch command {
	case "login":
		login(program, args)
		break
	case "events":
		events(program, args)
		break
	default:
		usage(program)
	}
}
func usage(program string) {
	fmt.Printf("Usage: %s [login,events]\n", program)
	os.Exit(1)
}

func login(program string, args []string) {
	if len(args) != 2 {
		fmt.Printf("Usage: %s login <username> <server>\n", program)
		os.Exit(1)
	}

	username, args := pop(args)
	baseUrl, args := pop(args)

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
		Token:     token.Value(),
	})
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println("Login successful")
}

func events(program string, args []string) {
	command, args := pop(args)

	switch command {
	case "import":
		eventsImport(program, args)
		break;
	default:
		eventsUsage(program)
	}
}
func eventsUsage(program string) {
	fmt.Printf("Usage: %s events [import]\n", program)
	os.Exit(1)
}

func eventsImport(program string, args []string) {
	filename, _ := pop(args)

	if filename == "" {
		eventsImportUsage(program)
	}

	preferences := loadPreferences(program)

	eventResource := ws.NewEventResource(
		net.NewRemoteServer(preferences.ServerUrl),
		ws.NewToken(preferences.Token),
	)

	result, err := eventResource.Future()
	if err != nil {
		log.Fatal(err)
	}

	fmt.Printf("Result: %s\n", result)
}
func eventsImportUsage(program string) {
	fmt.Printf("Usage: %s events import <filename.csv>\n", program)
	os.Exit(1)
}

func loadPreferences(program string) (*prefs.Preferences) {
	preferences, err := prefs.Load()
	if err != nil {
		log.Fatal(err)
	}

	if preferences == nil {
		fmt.Printf("Please login before executing this command with: %s login\n", program)
		os.Exit(1)
	}

	return preferences
}
