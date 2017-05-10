package main

import (
	"cpollet.net/itinerants/cmd"
	"cpollet.net/itinerants/helpers"
	"os"
)

func main() {
	program, args := helpers.Pop(os.Args)
	command, args := helpers.Pop(args)

	switch command {
	case "login":
		cmd.Login(program, args)
		break
	case "events":
		cmd.Events(program, args)
		break
	case "help":
		helpers.Die("CSV format: event;day;month:year;time (hh:mm);people")
	default:
		usage(program)
	}
}

func usage(program string) {
	helpers.Dief("Usage: %s [help,login,events]", program)
}
