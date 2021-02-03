   public void move(PlayerMoveEvent e) {
        Location from = e.getFrom();
        if (from.getBlock().getType() == Material.SAND) {
            setAir(from);

            boolean aboutX = false;
            if (isAbout(from.getX())) {
                aboutX = true;
                setAir(from, new Vector(1, 0, 0));
            }
            if (isAbout(from.getZ())) {
                setAir(from, new Vector(0, 0, 1));
                if (aboutX) {
                    setAir(from, new Vector(1, 0, 1));
                }
            }

        }
    }

    private void setAir(Location from) {
        from.getBlock().setType(Material.AIR);
        from.add(0, -1, 0).getBlock().setType(Material.AIR);
        from.add(0, 1, 0);
    }

    private void setAir(Location from, Vector vector) {
        from.add(vector);
        setAir(from);
        from.add(vector.multiply(-1));
    }

    private boolean isAbout(double val) {
        double part = val - (int) val;
        return part > 0.25 && part < 0.75;
    }
