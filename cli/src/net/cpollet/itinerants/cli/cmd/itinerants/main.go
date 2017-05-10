package main

import (
	"os"
	"net/cpollet/itinerants/cli/cmd"
	"net/cpollet/itinerants/cli/helpers"
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
	default:
		usage(program)
	}
}

func usage(program string) {
	helpers.Dief("Usage: %s [login,events]\n", program)
}
