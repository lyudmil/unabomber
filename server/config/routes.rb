Server::Application.routes.draw do
  resources :players, :only => [:create, :show, :index]
  resources :bombs, :only => [:show, :index]
  resources :messages, :only => :show
  
  #This makes the client work, so it is necessary. It should be unnecessary and use the resource route defined above.
  post 'players/create' => 'players#create'
	get 'players' => 'players#index'
  put 'players/:device_id/update' => 'players#update'
	post 'players/:device_id/arrest' => 'players#arrest'
	
	post ':device_id/bombs/place' => 'bombs#place'
	post 'bombs/:id/detonate' => 'bombs#detonate'
	
	put ':device_id/messages/create' => 'messages#create'
	get ':device_id/messages' => 'messages#index'
 	
	get ':device_id/status' => 'status#index'
end
