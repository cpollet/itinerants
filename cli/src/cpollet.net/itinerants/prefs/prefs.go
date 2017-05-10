package prefs

import (
	"encoding/json"
	"errors"
	"fmt"
	"os"
	"os/user"
	"path/filepath"
)

const prefFileName = ".itinerants.json"

type Preferences struct {
	ServerUrl string
	Token     string
}

type filePreferences struct {
	Url   string `json:"url"`
	Token string `json:"token"`
}

func Load() (*Preferences, error) {
	path, err := home()
	if err != nil {
		return nil, err
	}

	file, err := os.Open(filepath.Join(path, prefFileName))

	if err != nil {
		if os.IsNotExist(err) {
			return nil, nil
		}
		return nil, errors.New(fmt.Sprintf("Unable open ~/.itinerants.json: %s", err))
	}
	defer file.Close()

	var filePreference filePreferences
	if err := json.NewDecoder(file).Decode(&filePreference); err != nil {
		return nil, errors.New(fmt.Sprintf("Unable to parse JSON: %s", err))
	}

	return &Preferences{
		ServerUrl: filePreference.Url,
		Token:     filePreference.Token,
	}, nil
}

func home() (string, error) {
	usr, err := user.Current()
	if err != nil {
		return "", err
	}
	return usr.HomeDir, nil
}

func Save(preferences Preferences) error {
	payload, err := json.Marshal(filePreferences{
		Url:   preferences.ServerUrl,
		Token: preferences.Token,
	})
	if err != nil {
		return err
	}

	path, err := home()
	if err != nil {
		return err
	}

	file, err := os.Create(filepath.Join(path, prefFileName))

	if err != nil {
		return errors.New(fmt.Sprintf("Unable write ~/.itinerants.json: %s", err))
	}
	defer file.Close()

	file.Write(payload)
	return nil
}
