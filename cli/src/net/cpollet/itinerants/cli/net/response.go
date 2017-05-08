package net

import "io"

type Response interface {
	HttpCode() int
	Content() io.Reader
}

type response struct {
	httpCode int
	content  io.Reader
}

func NewResponse(httpCode int, content io.Reader) Response {
	return response{
		httpCode: httpCode,
		content:  content,
	}
}

func (r response) HttpCode() int {
	return r.httpCode
}

func (r response) Content() io.Reader {
	return r.content
}
