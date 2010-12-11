Server::Application.routes.draw do
  resources :players, :only => [:create, :show]
  
  put 'players/:device_id/update' => 'players#update'
end
