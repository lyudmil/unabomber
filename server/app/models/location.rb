class Location < ActiveRecord::Base
  belongs_to :player
	belongs_to :bomb
	acts_as_mappable :default_units => :kms, :lat_column_name => :latitude, :lng_column_name => :longitude
end
