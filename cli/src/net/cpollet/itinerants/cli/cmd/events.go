package cmd

import (
	"encoding/csv"
	"fmt"
	"net/cpollet/itinerants/cli/helpers"
	"net/cpollet/itinerants/cli/net"
	"net/cpollet/itinerants/cli/prefs"
	"net/cpollet/itinerants/cli/ws"
	"os"
	"strconv"
	"time"
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
		helpers.Dief("Unable open %s (%s)", filename, err)
	}
	defer file.Close()

	reader := csv.NewReader(file)

	events, err := reader.ReadAll()
	if err != nil {
		helpers.Dief("Unable to parse %s (%s)", filename, err)
	}

	preferences, err := prefs.Load()
	if err != nil {
		helpers.Dief("Unable to load preferences: %s", err)
	}
	if preferences == nil {
		helpers.Dief("Please login (%s login)", program)
	}

	eventResource := ws.NewEventResource(
		net.NewRemoteServer(preferences.ServerUrl),
		ws.NewToken(preferences.Token),
	)

	type eventData struct {
		title    string
		dateTime time.Time
	}
	parsedEvents := make([]eventData, len(events))

	for index, event := range events {
		name, day, month, year, hour := event[0], event[1], event[2], event[3], event[4]

		parsedDay, err := strconv.Atoi(day)
		if err != nil {
			helpers.Dief("Unable to parse day on line %d (%s is not a valid day)", index, day)
		}

		parsedMon, err := strconv.Atoi(month)
		if err != nil {
			helpers.Dief("Unable to parse month on line %d (%s is not a valid month)", index, month)
		}

		parsedYear, err := strconv.Atoi(year)
		if err != nil {
			helpers.Dief("Unable to parse year on line %d (%s is not a valid year)", index, year)
		}

		parsedHour, err := time.Parse("15:04", hour)
		if err != nil {
			helpers.Dief("Unable to parse hour on line %d (%s is not a valid hour)", index, hour)
		}

		eventDate := parsedHour.Add(-30*time.Minute).AddDate(parsedYear, parsedMon-1, parsedDay-1)

		parsedEvents[index] = eventData{
			title:    name,
			dateTime: eventDate,
		}
	}

	var workersSem = make(chan int, 3)
	var workerNotification = make(chan int, len(parsedEvents))

	defer func(numberOfEvents int) {
		for i := 0; i < numberOfEvents; i++ {
			<-workerNotification
		}
		fmt.Println("Done.")
	}(len(parsedEvents))

	for index, event := range parsedEvents {
		workersSem <- 1

		go func(index int, event eventData) {
			fmt.Printf("Creating [%d: %s]\n", index, event.title)
			err := eventResource.Create(event.title, event.dateTime)
			if err != nil {
				fmt.Printf("Error while creating [%d: %s]: %s\n", index, event.title, err)
			}

			workerNotification <- 1
			<-workersSem
		}(index, event)
	}
}

func eventsImportUsage(program string) {
	helpers.Dief("Usage: %s events import <filename.csv>", program)
}
