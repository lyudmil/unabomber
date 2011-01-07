Server::Application.routes.draw do
  resources :players, :only => [:create, :show]
  resources :locations, :only => :index
  
  #This makes the client work, so it is necessary. It should be unnecessary and use the resource route defined above.
  post 'players/create' => 'players#create'
  
  put 'players/:device_id/update' => 'players#update'
	
	post ':device_id/bombs/place' => 'bombs#place'
end
