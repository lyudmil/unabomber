Server::Application.routes.draw do
  post 'players/create' => 'players#create'
  put 'players/:device_id/update' => 'players#update'
end
