Server::Application.routes.draw do
  resources :players, :only => :create
  put 'players/:device_id/update' => 'players#update'
end
