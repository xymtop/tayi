package main

import (
	"fmt"
	"os"
)

func main() {
	args := os.Args
	fmt.Println(fmt.Sprintf("Hi,my name is TaYi.Hi,%s,long time no see!", args[1]))
}
