Server::Application.routes.draw do
  resources :players, :only => [:create, :show]
  
  post 'players/create' => 'players#create'
  put 'players/:device_id/update' => 'players#update'
end
