package main

import (
	"fmt"
	"log"
	"net/cpollet/itinerants/cli/ws"
)

func main() {
	token, err := ws.Token("cpollet", "password")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Printf("Token: %s\n", token.Token)

	result, err := ws.FutureEvents(token)

	fmt.Println(result)
}
