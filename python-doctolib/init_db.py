from app.models.base import Base, engine

def init_db():
    Base.metadata.create_all(bind=engine)

if __name__ == "__main__":
    print("Creando tablas en la base de datos...")
    init_db()
    print("¡Tablas creadas exitosamente!") 