package helpers

import (
	"fmt"
	"os"
)

func Pop(args []string) (string, []string) {
	if len(args) == 0 {
		return "", []string{}
	}

	return args[0], args[1:]
}

func Die(message interface{}) {
	fmt.Print(message)
	os.Exit(1)
}

func Dief(format string, a ...interface{}) {
	Die(fmt.Sprintf(format, a...))
}
