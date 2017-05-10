package ws

func NewToken(value string) Token {
	return token{value: value}
}

type Token interface {
	Value() string
}

type token struct {
	value string
}

func (token token) Value() string {
	return token.value
}
