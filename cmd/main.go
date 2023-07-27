package main

import (
	"fmt"
	"monkey_lang/repl"
	"os"
	user "os/user"
)

func main() {
	currentUser, err := user.Current()
	if err != nil {
		panic(err)
	}

	fmt.Printf("Hello %s! This is the Monkey programming language!\n", currentUser.Username)
	fmt.Printf("Feel free to type in commands\n")
	repl.Start(os.Stdin, os.Stdout)
}
