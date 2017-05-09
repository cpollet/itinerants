package main

import (
	"encoding/csv"
	"fmt"
	"golang.org/x/crypto/ssh/terminal"
	"log"
	"net/cpollet/itinerants/cli/net"
	"net/cpollet/itinerants/cli/prefs"
	"net/cpollet/itinerants/cli/ws"
	"os"
	"strconv"
	"strings"
	"syscall"
	"time"
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
		break
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

	file, err := os.Open(filename)
	if err != nil {
		fmt.Printf("Unable open %s (%s)\n", filename, err)
		os.Exit(1)
	}
	defer file.Close()

	reader := csv.NewReader(file)

	events, err := reader.ReadAll()
	if err != nil {
		fmt.Printf("Unable to parse %s (%s)\n", filename, err)
		os.Exit(1)
	}

	preferences := loadPreferences(program)

	eventResource := ws.NewEventResource(
		net.NewRemoteServer(preferences.ServerUrl),
		ws.NewToken(preferences.Token),
	)

	type eventData struct {
		title    string
		dateTime time.Time
	}
	parsedEvents := make([]struct {
		title    string
		dateTime time.Time
	}, len(events))

	for index, event := range events {
		name, day, month, year, hour, _ := event[0], event[1], event[2], event[3], event[4], event[5]

		parsedDay, err := strconv.Atoi(day)
		if err != nil {
			fmt.Printf("Unable to parse day on line %d (%s is not a valid day)\n", index, day)
			os.Exit(1)
		}

		parsedMon, err := strconv.Atoi(month)
		if err != nil {
			fmt.Printf("Unable to parse month on line %d (%s is not a valid month)\n", index, month)
			os.Exit(1)
		}

		parsedYear, err := strconv.Atoi(year)
		if err != nil {
			fmt.Printf("Unable to parse year on line %d (%s is not a valid year)\n", index, year)
			os.Exit(1)
		}

		parsedHour, err := time.Parse("15:04", hour)
		if err != nil {
			fmt.Printf("Unable to parse hour on line %d (%s is not a valid hour)\n", index, hour)
			os.Exit(1)
		}

		eventDate := parsedHour.Add(-30*time.Minute).AddDate(parsedYear, parsedMon-1, parsedDay-1)

		parsedEvents[index] = eventData{
			title:    name,
			dateTime: eventDate,
		}
	}

	for _, event := range parsedEvents {
		err := eventResource.Create(event.title, event.dateTime)
		if err != nil {
			fmt.Printf("Unable to create event %s: %s\n", event, err)
		}
	}
	//result, err := eventResource.Future	()
	//if err != nil {
	//	log.Fatal(err)
	//}
	//
	//fmt.Printf("Result: %s\n", result)
}
func eventsImportUsage(program string) {
	fmt.Printf("Usage: %s events import <filename.csv>\n", program)
	os.Exit(1)
}

func loadPreferences(program string) *prefs.Preferences {
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
