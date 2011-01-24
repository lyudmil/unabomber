class DetermineIfPlayerIsArrested < ActiveRecord::Migration
  def self.up
    add_column :players, :arrested, :boolean, :default => false
  end

  def self.down
    remove_column :players, :arrested
  end
end
