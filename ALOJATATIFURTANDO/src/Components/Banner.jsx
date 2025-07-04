import React from 'react'
import BannerImg from '../assets/tati.png'

export default function InicioBanner () {
  return (
    <div
      className="relative bg-cover bg-center pt-16 h-[calc(100vh-64px)] w-full"
      style={{
        backgroundImage: `url(${BannerImg})`,
        backgroundPosition: 'left center',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover'
      }}
    >
      <main className="px-10 lg:px-24 z-10 text-white">
        <h2 className="text-2xl">Sua Loja de Jogos Virtuais</h2>
        <p className="mt-3 sm:mt-5 sm:max-w-xl text-6xl">
           A LOJA<br />TATI <br /> FURTANDO
        </p>
        <p className="absolute bottom-5 left-1/2 -translate-x-1/2 text-white text-xl">
              <span
                className="absolute inset-0 bg-black bg-opacity-50 rounded-md -z-10"
                style={{ 
                  top: '-0.25em',    
                  bottom: '-0.25em', 
                  left: '-0.5em',    
                  right: '-0.5em'    
                }}
              ></span>
            preço justo / confiável / sem burocracia
        </p>
      </main>
    </div>
  )
}
