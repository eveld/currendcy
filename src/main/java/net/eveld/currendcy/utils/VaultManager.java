package net.eveld.currendcy.utils;

import java.util.ArrayList;

import net.eveld.currendcy.block.entity.VaultEntity;

public class VaultManager {
  private ArrayList<VaultEntity> vaults = new ArrayList<VaultEntity>();

  static VaultManager instance = new VaultManager();

  public static VaultManager getInstance() {
    return instance;
  }

  public ArrayList<VaultEntity> getVaults() {
    return this.vaults;
  }
  
  public void addVault(VaultEntity vault) {
    this.vaults.add(vault);
  }

  public void removeVault(VaultEntity vault) {
    this.vaults.remove(vault);
  }
}
