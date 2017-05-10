package cmd

import (
	"fmt"
	"os"
	"strconv"
	"time"
	"encoding/csv"
	"net/cpollet/itinerants/cli/prefs"
	"net/cpollet/itinerants/cli/ws"
	"net/cpollet/itinerants/cli/helpers"
	"net/cpollet/itinerants/cli/net"
)

func Events(program string, args []string) {
	command, args := helpers.Pop(args)

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
	filename, _ := helpers.Pop(args)

	if filename == "" {
		eventsImportUsage(program)
	}

	file, err := os.Open(filename)
	if err != nil {
		helpers.Dief("Unable open %s (%s)\n", filename, err)
	}
	defer file.Close()

	reader := csv.NewReader(file)

	events, err := reader.ReadAll()
	if err != nil {
		helpers.Dief("Unable to parse %s (%s)\n", filename, err)
	}

	preferences, err := prefs.Load()
	if err != nil {
		helpers.Dief("Unable to load preferences: %s\n", err)
	}
	if preferences == nil {
		helpers.Dief("Please login (%s login)\n", program)
	}

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
		name, day, month, year, hour := event[0], event[1], event[2], event[3], event[4]

		parsedDay, err := strconv.Atoi(day)
		if err != nil {
			helpers.Dief("Unable to parse day on line %d (%s is not a valid day)\n", index, day)
		}

		parsedMon, err := strconv.Atoi(month)
		if err != nil {
			helpers.Dief("Unable to parse month on line %d (%s is not a valid month)\n", index, month)
		}

		parsedYear, err := strconv.Atoi(year)
		if err != nil {
			helpers.Dief("Unable to parse year on line %d (%s is not a valid year)\n", index, year)
		}

		parsedHour, err := time.Parse("15:04", hour)
		if err != nil {
			helpers.Dief("Unable to parse hour on line %d (%s is not a valid hour)\n", index, hour)
		}

		eventDate := parsedHour.Add(-30 * time.Minute).AddDate(parsedYear, parsedMon-1, parsedDay-1)

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
}

func eventsImportUsage(program string) {
	helpers.Dief("Usage: %s events import <filename.csv>\n", program)
}
