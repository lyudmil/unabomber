class AddAttributesToLocation < ActiveRecord::Migration
  def self.up
    add_column :locations, :longitude, :decimal
    add_column :locations, :latitude, :decimal
    add_column :locations, :altitude, :decimal
  end

  def self.down
    remove_column :locations, :longitude
    remove_column :locations, :latitude
    remove_column :locations, :altitude
  end
end
